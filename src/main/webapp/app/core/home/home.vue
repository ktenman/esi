<template>
  <div class='home row'>
    <div class='col-md-3'>
      <span class='hipster img-fluid rounded'></span>
    </div>
    <div class='col-md-9'>
      <h1 class='display-4'>Welcome, Java Hipster!</h1>
      <p class='lead'>This is your homepage</p>

      <div>
        <div v-if='authenticated' class='alert alert-success'>
          <span v-if='username'>You are logged in as user "{{ username }}"</span>
        </div>

        <div v-if='!authenticated' class='alert alert-warning'>
          <span>If you want to </span>
          <a class='alert-link' v-on:click='openLogin()'>sign in</a
          ><span
        >, you can try the default accounts:<br />- Administrator (login="admin" and password="admin") <br />- User (login="user" and
            password="user").</span
        >
        </div>
        <div v-if='!authenticated' class='alert alert-warning'>
          <span>You don't have an account yet?</span>&nbsp;
          <router-link class='alert-link' to='/register'>Register a new account</router-link>
        </div>
      </div>

      <p>If you have any question on JHipster:</p>

      <ul>
        <li><a href='https://www.jhipster.tech/' rel='noopener noreferrer' target='_blank'>JHipster homepage</a></li>
        <li>
          <a href='http://stackoverflow.com/tags/jhipster/info' rel='noopener noreferrer' target='_blank'>JHipster on
            Stack Overflow</a>
        </li>
        <li>
          <a href='https://github.com/jhipster/generator-jhipster/issues?state=open' rel='noopener noreferrer'
             target='_blank'
          >JHipster bug tracker</a
          >
        </li>
        <li>
          <a href='https://gitter.im/jhipster/generator-jhipster' rel='noopener noreferrer' target='_blank'>JHipster
            public chat room</a>
        </li>
        <li>
          <a href='https://twitter.com/jhipster' rel='noopener noreferrer' target='_blank'>follow @jhipster on
            Twitter</a>
        </li>
      </ul>

      <p>
        <span>If you like JHipster, don't forget to give us a star on</span>
        <a href='https://github.com/jhipster/generator-jhipster' rel='noopener noreferrer' target='_blank'>GitHub</a>!
      </p>
    </div>
  </div>
</template>

<script lang='ts' src='./home.component.ts'></script>
