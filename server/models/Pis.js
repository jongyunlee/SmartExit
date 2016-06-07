var mongoose = require('mongoose');

var PiSchema = new mongoose.Schema({
    temp: {type: Number, default: 0},
    humidity: {type:Number, default: 0},
    direction: {type: String, default: 'left'},
    location: String,
    state: {type: String, default: 'normal'},
    destination: String,
    message: String,
});

PiSchema.methods.changeTempHumidity = function(newTemp, newHumidity, cb) {
    this.temp = newTemp;
    this.humidity = newHumidity;
    this.save(cb);
};

PiSchema.methods.changeMessage = function(newMessage, cb) {
    this.message = newMessage;
    this.save(cb);
};

PiSchema.methods.changeDestination = function(newDestination, cb) {
    this.destination = newDestination;
    this.save(cb);
};

PiSchema.methods.changeState = function(newState, cb) {
    this.state = newState;
    this.save(cb);
};

PiSchema.methods.changeDirection = function(newDirection, cb) {
    this.direction = newDirection;
    this.save(cb);
};

mongoose.model('Pi', PiSchema);
