<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers Sign In </h1>


  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />
    <p>
        <label>User ID: </label>
        <input type = "text"
            id = "myText"
            value = "" />
        <input type="button"
            value="Submit">
    </p>


  </div>

</div>
</body>

</html>