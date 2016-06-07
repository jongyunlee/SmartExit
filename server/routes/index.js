var fs = require('fs');
var path = require('path');
var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var Pi = mongoose.model('Pi');
var formidable = require('formidable');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index');
});

router.param('pi', function(req, res, next, id) {
    var query = Pi.findById(id);
    query.exec(function(err, pi) {
	if (err) { return next(err); }
	if (!pi) { return next(new Error('can\'t find pi')); }

	req.pi = pi;
	return next();
    });
});

router.get('/pis', function(req, res, next) {
    Pi.find(function(err, pis) {
	if (err) {return next(err);}
	res.json(pis);
    });
});

router.get('/pis/:pi', function(req, res) {
    res.json(req.pi);
});

router.delete('/pis/:pi', function(req, res) {
    // res.json(req.pi);
    console.log('delete');
    req.pi.remove(function(err) {
	if (err) {return next(err);}
	console.log('req.pi');
	res.json(req.pi);
    });
});

router.post('/pis', function(req, res, next) {
    var pi = new Pi(req.body);
    pi.save(function(err, pi) {
	if (err) { return next(err); }
	res.json(pi);
    });
});

router.put('/pis/:pi/temphum', function(req, res, next) {
    req.pi.changeTempHumidity(req.body.temp, req.body.humidity, function(err, pi) {
	if (err) { return next(err); }
	res.json(pi);
    });
});

router.put('/pis/:pi/message', function(req, res, next) {
    console.log(req.body.message);
    req.pi.changeMessage(req.body.message, function(err, pi) {
	if (err) {
	    return next(err);
	}
	res.json(pi);
    });
});

router.put('/pis/:pi/destination', function(req, res, next) {
    console.log(req.body.destination);
    req.pi.changeDestination(req.body.destination, function(err, pi) {
	if (err) {
	    return next(err);
	}
	res.json(pi);
    });
});

router.put('/pis/:pi/state', function(req, res, next) {
    console.log(req.body.state);
    req.pi.changeState(req.body.state, function(err, pi) {
	if (err) {
	    return next(err);
	}
	res.json(pi);
    });
});

router.put('/pis/:pi/direction', function(req, res, next) {
    console.log(req.body.direction);
    req.pi.changeDirection(req.body.direction, function(err, pi) {
	if (err) {
	    return next(err);
	}
	res.json(pi);
    });
});

router.post('/pis/:pi/image', function(req, res, next) {
    var form = new formidable.IncomingForm();
    form.uploadDir = __dirname + '/../public/images/' + req.pi._id + '.jpg';
    form.keepExtensions = true;
    form.on('error', function(err) {
	console.log('err');
    }).on('field', function(field, value) {
	console.log(field, value);
    }).on('fileBegin', function(name, file) {
	file.path = form.uploadDir;
    }).on('file', function(field, file) {
	console.log(file);
    }).on('progress', function(bytesReceived, bytesExpected) {
	console.log('progress');
	var percent = (bytesReceived / bytesExpected * 100) | 0;
	process.stdout.write('Uploading: %' + percent + '\r');
    }).on('end', function(req, res) {
	console.log('form end:\n\n');
    });
    form.parse(req, function(err) {
	// res.redirect('/');
	res.json({success: true});
	console.log('form parse: \n\n');
    });
});

module.exports = router;
