<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>

<!--     <script type="text/javascript src="js/plupload.full.min.js"></script> -->
    <script src="js/jquery-1.6.min.js" type="text/javascript"></script>
	<script src="js/jquery.reflection.js"></script>
  	<script src="js/jquery.cloud9carousel.js"></script>
  	<script>window.jQuery || document.write('<script src="js/jquery-1.6.min.js"><\/script>')</script>
  	
  	<link rel="stylesheet" type="text/css" href="css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="css/default.css">
	<link rel="stylesheet" href="css/common.css">
  	
   <style>
	body{
	background: #16235e;
      background: -moz-linear-gradient(top, #16235e 0%, #020223 100%); /* FF3.6+ */
      background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#16235e), color-stop(100%,#020223)); /* Chrome, Safari4+ */
      background: -webkit-linear-gradient(top, #16235e 0%, #020223 100%); /* Chrome10+, Safari5.1+ */
      background: -o-linear-gradient(top, #16235e 0%, #020223 100%); /* Opera 11.10+ */
      background: -ms-linear-gradient(top, #16235e 0%, #020223 100%); /* IE10+ */
      background: linear-gradient(to bottom, #16235e 0%, #020223 100%); /* W3C */
	}
    a {
      color: #da2020;
    }
    a:hover {
      color: #d6f300;
    }
    .wrap > h1 {
      margin: 26px auto;
    }
    #showcase {
      height: 460px;
      
      border-radius: 8px;
    }
    #item-title {
      color: #F31414;
      font-size: 29px;
      letter-spacing: 0.13em;
      text-shadow: 1px 1px 6px #C72B2B;
      text-align: center;
      margin-top: 30px;
      margin-bottom: 22px;
    }
    #nav {
      margin-top: 10px;
      text-align: center;
    }
    #nav > button {
      width: 64px;
      height: 36px;
      color: #666;
      font: bold 16px arial;
      text-align: center;
      margin: 5px;
      text-shadow: 0px 1px 0px #f5f5f5;
      background: #f6f6f6;
      border: solid 2px rgba(0, 0, 0, 0.4);
      -webkit-border-radius: 5px;
      -moz-border-radius: 5px;
      border-radius: 5px;
      -webkit-box-shadow: 0 0 9px 1px rgba(0, 0, 0, 0.4);
      -moz-box-shadow: 0 0 9px 1px rgba(0, 0, 0, 0.4);
      box-shadow: 0 0 9px 1px rgba(0, 0, 0, 0.4);
      cursor: pointer;
    }
    #nav > button:active,
    #nav > button.down {
      background: #dfdfdf;
      border: solid 2px rgba(0, 0, 0, 0.6);
      box-shadow: none;
    }
    #share {
      top: -9px;
    }
    #credits {
      top: -15px;
    }
  </style>
    
</head>
<body>
<div id="carousel">
  <img class="cloud9-item" src="images/browsers/chrome.png" alt="Item #1">
  <img class="cloud9-item" src="images/browsers/firefox.png" alt="Item #2">
  <img class="cloud9-item" src="images/browsers/iexplore.png" alt="Item #3">
  <img class="cloud9-item" src="images/browsers/opera.png" alt="Item #4">
  <img class="cloud9-item" src="images/browsers/safari.png" alt="Item #5">
  <img class="cloud9-item" src="images/browsers/wyzo.png" alt="Item #6">
</div>
 
<div id="buttons">
  <button class="left">
1
  </button>
  <button class="right">
2
  </button>
</div> 
</body>
<script>
    $(function() {
      var showcase = $("#showcase"), title = $('#item-title')

      showcase.Cloud9Carousel( {
        yOrigin: 42,
        yRadius: 48,
        mirror: {
          gap: 12,
          height: 0.2
        },
        buttonLeft: $("#nav > .left"),
        buttonRight: $("#nav > .right"),
        autoPlay: 1,
        bringToFront: true,
        onRendered: rendered,
        onLoaded: function() {
          showcase.css( 'visibility', 'visible' )
          showcase.css( 'display', 'none' )
          showcase.fadeIn( 1500 )
        }
      } )

      function rendered( carousel ) {
        title.text( carousel.nearestItem().element.alt )

        // Fade in based on proximity of the item
        var c = Math.cos((carousel.floatIndex() % 1) * 2 * Math.PI)
        title.css('opacity', 0.5 + (0.5 * c))
      }

      //
      // Simulate physical button click effect
      //
      $('#nav > button').click( function( e ) {
        var b = $(e.target).addClass( 'down' )
        setTimeout( function() { b.removeClass( 'down' ) }, 80 )
      } )

      $(document).keydown( function( e ) {
        //
        // More codes: http://www.javascripter.net/faq/keycodes.htm
        //
        switch( e.keyCode ) {
          /* left arrow */
          case 37:
            $('#nav > .left').click()
            break

          /* right arrow */
          case 39:
            $('#nav > .right').click()
        }
      } )
    })
  </script>

</html>