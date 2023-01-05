const http = require('http')
const API = require('./API')

const host = process.env.PORT

API.set('port', 3001)
const server = http.createServer(API)

server.listen(3001, host, ()=>{
    console.log("API démarrer")
})