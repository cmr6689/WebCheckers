 <div class="navigation">
  <#if currentUser??>
    <#assign currentUser = {"name":currentUser}>
    <a href="/">my home</a> |
    <form id="signout" name="signout" action="/signout" method="post">
     <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
     <input type="hidden" name="signout" value="${currentUser.name}">
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
