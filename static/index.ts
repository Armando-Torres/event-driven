Bun.serve({
    port: 8080,
    static: {
      // health-check endpoint
      "/health-check": new Response("All good!"),

      // serve static text
      "/": new Response(await Bun.file("./src/index.html").bytes(), {
        headers: {
          "Content-Type": "text/html",
        },
      })
    },
  
    fetch(req) {
      return new Response("404!");
    },
});