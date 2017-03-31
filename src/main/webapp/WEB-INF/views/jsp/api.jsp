<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/general_path.jsp"%>
<!DOCTYPE html>
<html>
<head>
	  <meta charset="UTF-8">
	  <title>Boot SWagger API</title>
	  <link href='${ctx}/static/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
	  <link href='${ctx}/static/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
	  <link href='${ctx}/static/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
	  <link href='${ctx}/static/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
	  <link href='${ctx}/static/css/screen.css' media='print' rel='stylesheet' type='text/css'/>
	  <script type="text/javascript" src="${ctx}/static/lib/shred.bundle.js"></script>
	  <script type="text/javascript" src="${ctx}/static/js/jquery-1.10.2.min.js"></script>
	  <script src='${ctx}/static/lib/jquery.slideto.min.js' type='text/javascript'></script>
	  <script src='${ctx}/static/lib/jquery.wiggle.min.js' type='text/javascript'></script>
	  <script src='${ctx}/static/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
	  <script src='${ctx}/static/lib/handlebars-2.0.0.js' type='text/javascript'></script>
	  <script src='${ctx}/static/lib/underscore-min.js' type='text/javascript'></script>
	  <script src='${ctx}/static/lib/backbone-min.js' type='text/javascript'></script>
	  <script src='${ctx}/static/lib/swagger-client.js' type='text/javascript'></script>
	  <script src='${ctx}/static/js/swagger-ui.js' type='text/javascript'></script>
	  <script src='${ctx}/static/lib/highlight.7.3.pack.js' type='text/javascript'></script>
	  <script src='${ctx}/static/lib/marked.js' type='text/javascript'></script>
	
	  <!-- enabling this will enable oauth2 implicit scope support -->
	  <script src='${ctx}/static/lib/swagger-oauth.js' type='text/javascript'></script>
	  <script type="text/javascript">
	    $(function () {
	      var url = window.location.search.match(/url=([^&]+)/);
	      if (url && url.length > 1) {
	        url = decodeURIComponent(url[1]);
	      } else {
	    	// 前台配置的url 
	        url = "${url}";
	      }
	      window.swaggerUi = new SwaggerUi({
	        url: url,
	        dom_id: "swagger-ui-container",
	        supportedSubmitMethods: ['get', 'post', 'put', 'delete'],
	        onComplete: function(swaggerApi, swaggerUi){
	          if(typeof initOAuth == "function") {
	            /*
	            initOAuth({
	              clientId: "your-client-id",
	              realm: "your-realms",
	              appName: "your-app-name"
	            });
	            */
	          }
	          $('pre code').each(function(i, e) {
	            hljs.highlightBlock(e)
	          });
	        },
	        onFailure: function(data) {
	          log("Unable to Load SwaggerUI");
	        },
	        docExpansion: "none",
	        sorter : "alpha"
	      });
	
	      function addApiKeyAuthorization() {
	        var key = $('#input_apiKey')[0].value;
	        log("key: " + key);
	        if(key && key.trim() != "") {
	            log("added key " + key);
	            window.authorizations.add("api_key", new ApiKeyAuthorization("api_key", key, "query"));
	        }
	      }
	
	      $('#input_apiKey').change(function() {
	        addApiKeyAuthorization();
	      });
	
	      // if you have an apiKey you would like to pre-populate on the page for demonstration purposes...
	      /*
	        var apiKey = "myApiKeyXXXX123456789";
	        $('#input_apiKey').val(apiKey);
	        addApiKeyAuthorization();
	      */
	
	      window.swaggerUi.load();
	  });
  </script>
</head>
<body class="swagger-section">
	<div id='header'>
	  <div class="swagger-ui-wrap">
	    <a id="logo" href="http://swagger.io">swagger</a>
	    <form id='api_selector'>
	      <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl" type="text"/></div>
	      <div class='input'><input placeholder="api_key" id="input_apiKey" name="apiKey" type="text"/></div>
	      <div class='input'><a id="explore" href="#">Explore</a></div>
	    </form>
	  </div>
	</div>
	<div id="message-bar" class="swagger-ui-wrap">&nbsp;</div>
	<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
</body>
</html>