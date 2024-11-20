# Clubs - Clan and Friends System for Minecraft

Clubs is an advanced Minecraft plugin influenced by [MinemenClub](https://minemen.club) that brings clan systems and friend management to your server, designed for robust and scalable performance using Redis and MongoDB.

This project is **open-source**, with the goal of enabling others to learn, contribute, and create an enhanced Minecraft server experience. It was originally developed by [imhieu](https://github.com/imhieu) and later acquired by me, **@Emmy**.

---

## 🚀 Features

**- Works across Bungee.**

### **Clubs (Clan System)**
- **Create and manage clubs**: Form a community with your friends.
- **Roles and permissions**: Assign roles to club members.
- **Invite, accept, or kick members**: Fully customizable club management.
- **Club chat**: Communicate privately with your clan.

### **Friends System**
- **Add friends**: Connect with other players.
- **Manage friend lists**: Accept or remove friendships.
- **View online/offline status**: Always know who's online.

### **Backend Power**
- **Redis Integration**: Real-time communication and data updates.
- **MongoDB Integration**: Persistent, scalable database support.

---

## 📦 Installation

1. Clone this repository and compile the plugin on an IDE.
2. Add the compiled `.jar` file to your Minecraft server's `/plugins` directory.
3. Configure the `config.yml` file:
    - Set up **MongoDB** and **Redis** credentials.
4. Start or reload your server to initialize the plugin.

---

## 🛠️ Commands

### **Friends Commands**
- `/friends list` - View your friend list.
- `/friends add <name>` - Send a friend request.
- `/friends accept <name>` - Accept a friend request.
- `/friends remove <name>` - Remove a friend.

### **Clubs Commands**
- `/clubs create <name>` - Create a new club.
- `/clubs invite <name>` - Invite a player to your club.
- `/clubs accept` - Accept a club invitation.
- `/clubs role <name> <role>` - Assign a role to a club member.
- `/clubs chat` - Toggle club chat.
- `/clubs disband` - Disband your club.

... and more!

---

## 🤝 Contributing

Feel free to fork this project, submit issues, and send pull requests. Let's collaborate to make **Clubs** even better!

---

## 💡 Authors

- **Original Author**: [imhieu](https://github.com/imhieu)
- **Ownership**: This project was acquired and is now maintained by **@Emmy**.

---

**License:** This project is licensed under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
