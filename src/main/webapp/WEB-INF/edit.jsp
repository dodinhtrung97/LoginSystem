<html>
    <head>
    <title>Edit User Profile</title>
    </head>

    <body>

        <h1>Edit User Profile</h1>
        <P><B>Please edit user information below:</B></P>

        <form action="/edit" method="post">

            ID<br/>
            <P><input type="text" name="name">
            <br/>

            Name<br/>
            <P><input type="text" name="id" value="">
            <br/>

            Enter a new password (or leave blank to keep password the same)<br/>
            <P><input type="text" name="password" value="">
            <br/>
            <br><br>
            <P><input type="submit" name="submitbtn" value="Change User Profile">

         </form>

    </body>
</html>