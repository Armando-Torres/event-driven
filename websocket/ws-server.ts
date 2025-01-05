import type { ServerWebSocket } from "bun";

const clients = new Set<ServerWebSocket<unknown>>();

const server = Bun.serve({
    port: 3000,
    fetch(req, server) {
      const userAgent = req.headers.get('user-agent');
      
      server.upgrade(req, {
        data: {
          listener: (userAgent)
        },
      });
  
      return undefined;
    },
    websocket: {
      async open(ws) {
          console.log(`Connection opened: ${ws.remoteAddress}`);
          // @ts-ignore
          if (ws.data.listener) {
            clients.add(ws);
          }
      },
      async close(ws, code, reason) {
        console.log(`Connection closed: ${ws.remoteAddress} with code: ${code} ${reason}`);
        // @ts-ignore
        if (ws.data.listener) {
          clients.delete(ws);
        }
      },
      async message(ws, message) {
        // Broadcast the received message to all listener role connected clients
        for (const client of clients) {
          if (client.readyState === WebSocket.OPEN) {
            client.send(message as string);
            console.log(`Sent back to ${client.remoteAddress}: ${message}`);
          }
        }
      },
    },
  });
  
  console.log(`Listening on localhost:${server.port}`);