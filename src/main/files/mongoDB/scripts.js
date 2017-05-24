
// Create index
db.getCollection('gutenberg').createIndex({'cities.loc':"2dsphere"});

// Find near
// Info: https://docs.mongodb.com/manual/reference/operator/query/near/#query-on-geojson-data
var longitude = 55.94320000;
var latitude = 25.78953000;
var minDistance = 0.001; // unit = meter
var maxDistance = 1000000; // unit = meter
db.getCollection('gutenberg').find({
    'cities.loc':
        { $near :
            {
                $geometry: { type: "Point",  coordinates: [ longitude, latitude ] },
                $minDistance: minDistance,
                $maxDistance: maxDistance
            }
        }
});