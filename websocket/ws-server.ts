const clients = new Set<any>();

const server = Bun.serve({
    port: 3000,
    fetch(req, server) {
      // upgrade the request to a WebSocket
      if (server.upgrade(req)) {
        return; // do not return a Response
      }
      return new Response("Upgrade failed", { status: 500 });
    },
    websocket: {
      async open(ws) {
          console.log(`Connection opened: ${ws.remoteAddress}`);
          clients.add(ws);
      },
      async close(ws, code, reason) {
        console.log(`Connection closed: ${ws.remoteAddress}`);
        clients.delete(ws);
      },
      async message(ws, message) {
        // Ensure message is sent only if the WebSocket is open
        if (ws.readyState === WebSocket.OPEN) {
          // @ts-ignore
          ws.send(message);
          console.log(`Sent back: ${message}`);
        }

        // Broadcast the received message to all connected clients
        for (const client of clients) {
          if (client.readyState === WebSocket.OPEN) {
            client.send(message as string); // Send message to the other clients
          }
        }
      },
    },
  });
  
  console.log(`Listening on localhost:${server.port}`);