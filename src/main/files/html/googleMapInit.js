function initMap() {
    var locations = locs;

    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 3,
        center: new google.maps.LatLng(cLoc[0], cLoc[1]),
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        styles: customStyle
    });

    for (var i = 0; i < locations.length; i++) {
        new google.maps.Marker({
            label : locations[i][0],
            animation: google.maps.Animation.DROP,
            position: new google.maps.LatLng(locations[i][1], locations[i][2]),
            map: map
        });
    }
}