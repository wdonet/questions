<header>
    <div class="header-wrapper">
        <a href="/home"><img src="/img/logo.png" class="logo"></a>
        <nav class="menu-logout">
            <ul>
                <li><a href="/logout" class="logout">Logout</a></li>
            </ul>
        </nav>
        <nav class="menu-profile-photo">
            <a href="/profile"><img class="profile-header-photo"
                                    src="${(user.imageUrl)!"/img/no-photo.png"}"></a>
        </nav>
        <nav class="menu">
            <ul>
                <li><a href="/tags" class="category">Tags</a></li>
                <li><a href="/question/order/unanswered"
                       class="category unanswered-link">Unanswered</a></li>
                <li><a href="/question/order/newest" class="category newest-link">Newest</a></li>
                <li>
                    <div class="notifications-icon"><i class="fa fa-bell"></i></div>
                </li>
                <li><a class="config-icon" href="/admin/index"><i class="fa fa-cog"></i></a></li>
                <li><a href="/profile" class="user-info">${(user.fullName)!""}</a></li>
            </ul>
        </nav>
    </div>
</header>
