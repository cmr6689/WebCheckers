<!DOCTYPE html>

<head>
  <link rel="shortcut icon" href="/img/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />


    <#if currentUser??>
        <h2>Players Online</h2>
        <#if playerList??>
            <#if (playerList?size > 1)>
                <ul>
                    <#list playerList as player>
                        <#if currentUser.name != player>
                            <li><form method="get" action="/game" target="_self">
                                <input type="submit" name="opponent" value=${player}></form>
                        </#if>
                    </#list>
                </ul>
            <#else>
                <ul>
                    <li>There are no other players online.</li>
                </ul>
            </#if>
        <#else>
            <ul>
                <li>There are no other players online.</li>
            </ul>
        </#if>
    <#else>
        <h2>Players Online</h2>
        <#if playerList??>
            <#if (playerList?size > 1)>
                <ul><li>${playerList?size} players online.</li></ul>
            <#else>
                <ul><li>${playerList?size} player online.</li></ul>
            </#if>
        <#else>
            <ul><li>There are no players online.</li></ul>
        </#if>
    </#if>


  </div>

</div>
</body>

</html>
