<html>
    <head>
    <title>Edit User Profile</title>
    </head>

    <body>

        <h1>Edit User Profile</h1>
        <P><B>Please edit user information below:</B></P>

        <form action="/edit" method="post">

            ID of the user you want to change information:<br/>
            <input type="text" name="id" value="">
            <br/>

            Enter a new password (or leave blank to keep password the same):<br/>
            <input type="text" name="password" value="">
            <br/>

            Name:<br/>
            <input type="text" name="name" value="">
            <br/>

            <br><br>
            <input type="submit" name="submitbtn" value="Change User Profile">

         </form>

    </body>
</html>