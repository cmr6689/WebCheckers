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
        <form action="/" method="post">
          User ID: <input type="text" name="id">
          <input type="submit" value="Submit">
        </form>
    </p>


  </div>

</div>
</body>

</html>
