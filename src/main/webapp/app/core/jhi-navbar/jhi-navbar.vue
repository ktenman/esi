<template>
  <b-navbar class='jh-navbar' data-cy='navbar' toggleable='md' type='dark'>
    <b-navbar-brand b-link class='logo' to='/'>
      <span class='logo-img'></span>
      <span class='navbar-title'>library</span> <span class='navbar-version'>{{ version }}</span>
    </b-navbar-brand>
    <b-navbar-toggle
      aria-expanded='false'
      aria-label='Toggle navigation'
      class='jh-navbar-toggler d-lg-none'
      data-toggle='collapse'
      href='javascript:void(0);'
      right
      target='header-tabs'
    >
      <font-awesome-icon icon='bars' />
    </b-navbar-toggle>

    <b-collapse id='header-tabs' is-nav>
      <b-navbar-nav class='ml-auto'>
        <b-nav-item exact to='/'>
          <span>
            <font-awesome-icon icon='home' />
            <span>Home</span>
          </span>
        </b-nav-item>
        <b-nav-item-dropdown v-if='authenticated' id='entity-menu' active-class='active' class='pointer'
                             data-cy='entity'
                             right>
          <span slot='button-content' class='navbar-dropdown-menu'>
            <font-awesome-icon icon='th-list' />
            <span class='no-bold'>Entities</span>
          </span>
          <entities-menu></entities-menu>
          <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
        </b-nav-item-dropdown>
        <b-nav-item-dropdown
          v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"
          id='admin-menu'
          :class="{ 'router-link-active': subIsActive('/admin') }"
          active-class='active'
          class='pointer'
          data-cy='adminMenu'
          right
        >
          <span slot='button-content' class='navbar-dropdown-menu'>
            <font-awesome-icon icon='users-cog' />
            <span class='no-bold'>Administration</span>
          </span>
          <b-dropdown-item active-class='active' to='/admin/user-management'>
            <font-awesome-icon icon='users' />
            <span>User management</span>
          </b-dropdown-item>
          <b-dropdown-item v-if='openAPIEnabled' active-class='active' to='/admin/docs'>
            <font-awesome-icon icon='book' />
            <span>API</span>
          </b-dropdown-item>
          <b-dropdown-item v-if='!inProduction' href='./h2-console/' target='_tab'>
            <font-awesome-icon icon='database' />
            <span>Database</span>
          </b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown
          id='account-menu'
          :class="{ 'router-link-active': subIsActive('/account') }"
          active-class='active'
          class='pointer'
          data-cy='accountMenu'
          href='javascript:void(0);'
          right
        >
          <span slot='button-content' class='navbar-dropdown-menu'>
            <font-awesome-icon icon='user' />
            <span class='no-bold'> Account </span>
          </span>
          <b-dropdown-item v-if='authenticated' active-class='active' data-cy='settings' tag='b-dropdown-item'
                           to='/account/settings'>
            <font-awesome-icon icon='wrench' />
            <span>Settings</span>
          </b-dropdown-item>
          <b-dropdown-item v-if='authenticated' active-class='active' data-cy='passwordItem' tag='b-dropdown-item'
                           to='/account/password'>
            <font-awesome-icon icon='lock' />
            <span>Password</span>
          </b-dropdown-item>
          <b-dropdown-item v-if='authenticated' id='logout' active-class='active' data-cy='logout'
                           v-on:click='logout()'>
            <font-awesome-icon icon='sign-out-alt' />
            <span>Sign out</span>
          </b-dropdown-item>
          <b-dropdown-item v-if='!authenticated' id='login' active-class='active' data-cy='login'
                           v-on:click='openLogin()'>
            <font-awesome-icon icon='sign-in-alt' />
            <span>Sign in</span>
          </b-dropdown-item>
          <b-dropdown-item
            v-if='!authenticated'
            id='register'
            active-class='active'
            data-cy='register'
            tag='b-dropdown-item'
            to='/register'
          >
            <font-awesome-icon icon='user-plus' />
            <span>Register</span>
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script lang='ts' src='./jhi-navbar.component.ts'></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
  color: #ccc;
}

.jh-navbar {
  background-color: #353d47;
  padding: 0.2em 1em;
}

.jh-navbar .profile-image {
  margin: -10px 0px;
  height: 40px;
  width: 40px;
  border-radius: 50%;
}

.jh-navbar .dropdown-item.active,
.jh-navbar .dropdown-item.active:focus,
.jh-navbar .dropdown-item.active:hover {
  background-color: #353d47;
}

.jh-navbar .dropdown-toggle::after {
  margin-left: 0.15em;
}

.jh-navbar ul.navbar-nav {
  padding: 0.5em;
}

.jh-navbar .navbar-nav .nav-item {
  margin-left: 1.5rem;
}

.jh-navbar a.nav-link,
.jh-navbar .no-bold {
  font-weight: 400;
}

.jh-navbar .jh-navbar-toggler {
  color: #ccc;
  font-size: 1.5em;
  padding: 10px;
}

.jh-navbar .jh-navbar-toggler:hover {
  color: #fff;
}

@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span {
    display: none;
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: middle;
  color: white;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 5px 15px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.logo-img {
  height: 100%;
  background: url('../../../content/images/logo-jhipster.png') no-repeat center center;
  background-size: contain;
  width: 100%;
  filter: drop-shadow(0 0 0.05rem white);
}
</style>
