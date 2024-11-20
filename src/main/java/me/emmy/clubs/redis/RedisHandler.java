package me.emmy.clubs.redis;

import com.google.gson.Gson;
import me.emmy.clubs.redis.packet.Packet;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.function.Consumer;

public class RedisHandler {
    private final String host;
    private final int port;
    private final String channel;
    private final String password;
    private JedisPool jedisPool;
    private final Gson gson;

    /**
     * create a new RedisHandler
     *
     * @param host the ip address of the redis server.
     * @param port the port of the redis server.
     */
    public RedisHandler(String host, int port, String channel, String password) {
        this.host = host;
        this.port = port;
        this.channel = channel;
        this.password = password;
        this.gson = new Gson();
    }

    /**
     * Attempts to make a connection to the
     * redis database with the specified credentials and
     * starts a thread for receiving messages
     */
    public void connect() {
        this.jedisPool = new JedisPool(host, port);
        if (!this.password.isEmpty()){
            this.jedisPool.getResource().auth(this.password);
        }

        new Thread(() -> {

            this.runCommand(redis -> {
                if (!this.password.isEmpty()){
                    redis.auth(this.password);
                }
                redis.subscribe(new JedisPubSub() {

                    @Override
                    public void onMessage(String channel, String message) {
                        try {
                            // Create the packet
                            String[] strings = message.split("||");
                            Object jsonObject = gson.fromJson(strings[0], Class.forName(strings[1]));
                            Packet packet = (Packet) jsonObject;

                            packet.onReceive();

                        } catch (Exception ignored) {
                        }
                    }
                }, channel);
            });
        }).start();

    }

    /**
     * Sends a packet through redis.
     *
     * @param packet the packet to be sent
     */
    public void sendPacket(Packet packet) {
        packet.onSend();

        new Thread(() ->
                runCommand(redis -> {
                    if (!this.password.isEmpty()){
                        redis.auth(this.password);
                    }
                    redis.publish(channel, packet.getClass().getName() + "||" + gson.toJson(packet));
                })).start();
    }

    /**
     * Run a command on the redis server
     *
     * @param consumer the command to be run
     */
    private void runCommand(Consumer<Jedis> consumer) {
        Jedis jedis = jedisPool.getResource();
        if (jedis != null) {
            consumer.accept(jedis);
            jedisPool.returnResource(jedis);
        }
    }
}