<header>
    <div class="header-wrapper">
        <a href="/"> <img src="/img/logo.png" class="logo"></a>

        <form name="go_signin" id="go_signin" action="/auth/google" method="POST">
            <input type="submit" value="Sign In"/>
            <input type="hidden" name="scope"
                    value="profile email"/>
        </form>
    </div>
</header>
