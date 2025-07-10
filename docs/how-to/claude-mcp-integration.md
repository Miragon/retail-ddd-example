# 🤖 Claude Meets Our Shop: MCP Server Integration Guide

Welcome to the wild world of **Model Context Protocol (MCP)** integration!
This guide will show you how to connect MCP clients to our "Nerd Alert Shop" MCP server.

## 🤔 What is MCP?

**Model Context Protocol (MCP)** is an open standard that enables AI assistants to securely connect to external data
sources and tools. Think of it as a universal translator that lets AI models talk to your applications, databases, and
services in a standardized way.

**Key MCP Benefits:**

- 🔌 **Standardized Integration**: One protocol to connect AI to many different tools
- 🛡️ **Security**: Controlled access to external resources with proper authentication
- 🔄 **Real-time Data**: AI can fetch live data instead of relying on training data
- 🛠️ **Tool Integration**: AI can perform actions, not just answer questions

## 🎯 What's This MCP Madness?

Our shop backend doesn't just serve REST APIs -
it's also a fancy **MCP server** that can chat with any MCP-compatible client!
Think of it as giving AI assistants a direct hotline to our tech shop inventory.

**What MCP clients can do with our server:**

- 🔍 Browse all available articles
- 📱 Get detailed info about specific products
- 💬 Help you find the perfect tech gear
- 🛒 Basically become your personal tech shopping assistant

## 🚀 Setting Up with Claude Desktop

While our MCP server works with any MCP-compatible client,
we'll focus on Claude Desktop for this guide since it's a popular and well-documented option.

### Step 1: Find Your Config File

First, you'll need to locate your Claude Desktop configuration file.
It's hiding in different places depending on your OS:

**macOS:**

```bash
~/Library/Application Support/Claude/claude_desktop_config.json
```

**Windows:**

```bash
%APPDATA%\Claude\claude_desktop_config.json
```

### Step 2: Build the Shop Backend

Before Claude can talk to our shop, we need to build it:

```bash
./gradlew :services:shop:shop-backend:build
```

This creates the JAR file that Claude will use to start our MCP server.

### Step 3: Configure the MCP Magic

Open your `claude_desktop_config.json` file and add this configuration:

```json
{
  "mcpServers": {
    "nerd-alert-shop": {
      "name": "Nerd Alert Shop",
      "description": "A tech shop called nerd alert where you can buy all kinds of tech stuff.",
      "command": "java",
      "args": [
        "-Dspring.profiles.active=mcp-server",
        "-Dspring.ai.mcp.server.stdio=true",
        "-jar",
        "/path/to/your/project/services/shop/shop-backend/build/libs/shop-backend.jar"
      ]
    }
  }
}
```

### Step 4: Restart Claude Desktop

Close and reopen Claude Desktop.
You should now see the "Nerd Alert Shop" MCP server available in your Claude interface.

## 🎪 How It Works Under the Hood

Our MCP server exposes two main tools that Claude can use:

1. **`getArticles()`** - Fetches all available articles in our shop
2. **`getArticleById(id)`** - Gets detailed info about a specific article

These tools are implemented in `ArticleTools.kt` and configured via the `mcp-server` Spring profile.

## 🎉 Testing Your Setup

Once configured, you can test it by asking Claude things like:

- *"What tech products are available in the Nerd Alert Shop?"*
- *"Can you find me a laptop in the shop?"*
- *"Show me the details for article XYZ"*

Claude will use the MCP server to fetch real data from our shop backend and provide you with up-to-date information!

## 🛠️ Troubleshooting

**Claude can't find the shop?**

- Check that the JAR file path is correct
- Ensure Java is installed and available in your PATH
- Verify the project built successfully

**Server won't start?**

- Make sure no other process is using the same port
- Check that you have Java 21 installed
- Verify the Spring profile is set to `mcp-server`

**Articles not showing up?**

- The shop backend uses postgres that must be running in minikube
- Test data is automatically imported when the server starts
- Check the logs for any database connection issues
