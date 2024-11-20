package me.emmy.clubs.redis.packet;

public abstract class Packet {
    public abstract void onReceive();
    public abstract void onSend();
}
