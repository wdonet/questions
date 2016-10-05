<header>
    <div class="header-wrapper">
        <a href="/"> <img src="/img/logo.png" class="logo"></a>

        <form name="go_signin" id="go_signin" action="/auth/google" method="POST">
            <input type="submit" value="Sign In"/>
            <input type="hidden" name="scope"
                    value="profile email"/>
        </form>
        <nav class="menu">
            <ul>
                <li><a href="/tags" class="category">Tags</a></li>
                <li><a href="/question/order/unanswered" class="category unanswered-link">Unanswered</a></li>
                <li><a href="/question/order/newest" class="category newest-link">Newest</a></li>
                <li><div class="notifications-icon"><i class="fa fa-bell"></i></div></li>
                <li><a href="/profile" class="user-info">${(user.fullName)!""}</a></li>
            </ul>
        </nav>
    </div>
</header>
