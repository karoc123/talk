$( document ).ready(function() {
    
    $( ".button.up.true" ).addClass( "grey" ).removeClass("green");
    $( ".button.down.true" ).addClass( "grey" ).removeClass("red");
    console.log( "document loaded" );
});

$( window ).on( "load", function() {
    console.log( "window loaded" );
});