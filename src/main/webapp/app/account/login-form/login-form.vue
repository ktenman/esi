<template>
  <div class='modal-body'>
    <div class='row justify-content-center'>
      <div class='col-md-8'>
        <b-alert v-if='authenticationError' data-cy='loginError' show variant='danger'>
          <strong>Failed to sign in!</strong> Please check your credentials and try again.
        </b-alert>
      </div>
      <div class='col-md-8'>
        <b-form role='form' v-on:submit.prevent='doLogin()'>
          <b-form-group label='Username' label-for='username'>
            <b-form-input
              id='username'
              v-model='login'
              autofocus
              data-cy='username'
              name='username'
              placeholder='Your username'
              type='text'
            >
            </b-form-input>
          </b-form-group>
          <b-form-group label='Password' label-for='password'>
            <b-form-input
              id='password'
              v-model='password'
              v-model.trim='name'
              data-cy='password'
              name='password'
              placeholder='Your password'
              type='password'
            >
            </b-form-input>
          </b-form-group>
          <b-form-checkbox id='rememberMe' v-model='rememberMe' checked name='rememberMe'>
            <span>Remember me</span>
          </b-form-checkbox>
          <div>
            <b-button data-cy='submit' type='submit' variant='primary'>Sign in</b-button>
          </div>
        </b-form>
        <p></p>
        <div>
          <b-alert show variant='warning'>
            <b-link :to="'/account/reset/request'" class='alert-link' data-cy='forgetYourPasswordSelector'
            >Did you forget your password?
            </b-link
            >
          </b-alert>
        </div>
        <div>
          <b-alert show variant='warning'>
            <span>You don't have an account yet?</span>
            <b-link :to="'/register'" class='alert-link'>Register a new account</b-link>
          </b-alert>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./login-form.component.ts'></script>
