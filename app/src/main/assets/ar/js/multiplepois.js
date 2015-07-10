var World = {

	// Visibility radious in meters.
	visibilityRadius : 2000,

	markerDrawable_idle : new AR.ImageResource("assets/marker.png"), //new AR.ImageResource("assets/marker_idle.png"),

	// New: a second image resource which represents a selected marker
	markerDrawable_selected : new AR.ImageResource("assets/cuadro_realidad_p.png"), //new AR.ImageResource("assets/marker_selected.png"),

	// New: a array holding a reference to all marker objects
	markerList : [],

	init : function initFn() {

		AR.context.onLocationChanged = World.onLocationChanged;

		// New: a custom callback which will be invoked when the user taps on an empty screen space
		AR.context.onScreenClick = World.onScreenClick;
		
		// Restinge la visibilidad de los puntos.
		AR.context.scene.cullingDistance = World.visibilityRadius;
	},
	onLocationChanged : function onLocationChangedFn(latitude, longitude, altitude, accuracy) {

		AR.context.onLocationChanged = null;
	},
	onScreenClick : function onScreenClickFn() {
     
		for (var i = World.markerList.length - 1; i >= 0; i--) {
			if (World.markerList[i].isSelected) {
				World.markerList[i].setDeselected(World.markerList[i]);
			}
		}
        
	},
    showRange: function showRangeFn() {

        if (World.markerList.length > 0) {
        
            // update labels on every range movement
            $('#panel-distance-range').change(function() {
                                          World.updateRangeValues();
                                          });
        
            $('#panel-pois-range').change(function() {
                                      World.updateRangeValues();
                                      });

            World.updateRangeValues();

            World.handlePanelMovements();

            // open panel
            $("#panel-distance").trigger("updatelayout");
            $("#panel-distance").panel("open", 1234);
        } else {
        
            // no places are visible, because the are not loaded yet
            World.updateStatusMessage('No places available yet', true);
        }
    },
    
    handlePanelMovements: function handlePanelMovementsFn() {

        $("#panel-distance").on("panelclose", function(event, ui) {
                            $("#radarContainer").addClass("radarContainer_left");
                            $("#radarContainer").removeClass("radarContainer_right");
                            PoiRadar.updatePosition();
                            });
    
        $("#panel-distance").on("panelopen", function(event, ui) {
                            $("#radarContainer").removeClass("radarContainer_left");
                            $("#radarContainer").addClass("radarContainer_right");
                            PoiRadar.updatePosition();
                            });

    },
    
    // udpates values show in "range panel"
updateRangeValues: function updateRangeValuesFn() {

    // get current slider value (0..100) from distance;
    var slider_value = $("#panel-distance-range").val();
    
    // get current slider value (0..30) from number of pois
    var slider_value_pois = $("#panel-pois-range").val();
    
    // max range relative to the maximum distance of all visible places
    var maxRangeMeters = Math.round(slider_value * 1000);

    // range in meters including metric m/km
    var maxRangeValue = (maxRangeMeters > 999) ? ((maxRangeMeters / 1000).toFixed(2) + " km") : (Math.round(maxRangeMeters) + " m");
    
    // number of places within max-range or filtered by the number of pois slider (it takes the smallest one)
    var placesInRange = World.markerList.length;
    
    if (slider_value_pois < placesInRange){
        
        placesInRange = slider_value_pois;
        maxRangeMeters = Math.round(World.markerList[slider_value_pois].distanceToUser);
        maxRangeValue = (maxRangeMeters > 999) ? ((maxRangeMeters / 1000).toFixed(2) + " km") : (Math.round(maxRangeMeters) + " m");
        
    }
    
    // update UI labels accordingly
    $("#panel-distance-value").html(maxRangeValue);
    $("#panel-distance-places").html((placesInRange != 1) ? (placesInRange + " actividades") : (placesInRange + " Place"));
    // update culling distance, so only palces within given range are rendered
    AR.context.scene.cullingDistance = Math.max(maxRangeMeters, 1);
    // update radar's maxDistance so radius of radar is updated too
    PoiRadar.setMaxDistance(Math.max(maxRangeMeters, 1));
},
    
    // fired when user pressed maker in cam
onMarkerSelected: function onMarkerSelectedFn(marker) {
    World.currentMarker = marker;
    
    // update panel values
    $("#poi-detail-title").html(marker.poiData.title);
    $("#poi-detail-description").html(marker.poiData.description);
    
    var distanceToUserValue = (marker.distanceToUser > 999) ? ((marker.distanceToUser / 1000).toFixed(2) + " km") : (Math.round(marker.distanceToUser) + " m");
    
    $("#poi-detail-distance").html(distanceToUserValue);
    
    // show panel
    $("#panel-poidetail").panel("open", 123);
    
    $( ".ui-panel-dismiss" ).unbind("mousedown");
    
    $("#panel-poidetail").on("panelbeforeclose", function(event, ui) {
                             World.currentMarker.setDeselected(World.currentMarker);
                             });
},

/*
		It may make sense to display POI details in your native style.
		In this sample a very simple native screen opens when user presses the 'More' button in HTML.
		This demoes the interaction between JavaScript and native code.
	*/
	// user clicked "More" button in POI-detail panel -> fire event to open native screen
	onPoiDetailMoreButtonClicked: function onPoiDetailMoreButtonClickedFn() {
	  console.log("WORKS!");
		var currentMarker = World.currentMarker;
		var architectSdkUrl = "architectsdk://markerselected?id=" + encodeURIComponent(currentMarker.poiData.id);
		/*
			The urlListener of the native project intercepts this call and parses the arguments.
			This is the only way to pass information from JavaSCript to your native code.
			Ensure to properly encode and decode arguments.
			Note: you must use 'document.location = "architectsdk://...' to pass information from JavaScript to native.
			! This will cause an HTTP error if you didn't register a urlListener in native architectView !
		*/
		document.location = architectSdkUrl;
	},
    
};

World.init();

