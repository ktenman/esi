<template>
  <div>
    <div class='row justify-content-center'>
      <div class='col-md-8'>
        <h1>Activation</h1>
        <div v-if='success' class='alert alert-success'>
          <span><strong>Your user account has been activated.</strong> Please </span>
          <a class='alert-link' v-on:click='openLogin'>sign in</a>.
        </div>
        <div v-if='error' class='alert alert-danger'>
          <strong>Your user could not be activated.</strong> Please use the registration form to sign up.
        </div>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./activate.component.ts'></script>
