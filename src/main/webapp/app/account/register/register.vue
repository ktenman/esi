<template>
  <div>
    <div class='row justify-content-center'>
      <div class='col-md-8 toastify-container'>
        <h1 id='register-title' data-cy='registerTitle'>Registration</h1>

        <div v-if='success' class='alert alert-success' role='alert'>
          <strong>Registration saved!</strong> Please check your email for confirmation.
        </div>

        <div v-if='error' class='alert alert-danger' role='alert'><strong>Registration failed!</strong> Please try again
          later.
        </div>

        <div v-if='errorUserExists' class='alert alert-danger' role='alert'>
          <strong>Login name already registered!</strong> Please choose another one.
        </div>

        <div v-if='errorEmailExists' class='alert alert-danger' role='alert'>
          <strong>Email is already in use!</strong> Please choose another one.
        </div>
      </div>
    </div>
    <div class='row justify-content-center'>
      <div class='col-md-8'>
        <form v-if='!success' id='register-form' name='registerForm' no-validate role='form'
              v-on:submit.prevent='register()'>
          <div class='form-group'>
            <label class='form-control-label' for='username'>Username</label>
            <input
              id='username'
              v-model='$v.registerAccount.login.$model'
              :class='{ valid: !$v.registerAccount.login.$invalid, invalid: $v.registerAccount.login.$invalid }'
              class='form-control'
              data-cy='username'
              maxlength='50'
              minlength='1'
              name='login'
              pattern="^[a-zA-Z0-9!#$&'*+=?^_`{|}~.-]+@?[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$"
              required
              type='text'
            />
            <div v-if='$v.registerAccount.login.$anyDirty && $v.registerAccount.login.$invalid'>
              <small v-if='!$v.registerAccount.login.required' class='form-text text-danger'> Your username is
                required. </small>
              <small v-if='!$v.registerAccount.login.minLength' class='form-text text-danger'>
                Your username is required to be at least 1 character.
              </small>
              <small v-if='!$v.registerAccount.login.maxLength' class='form-text text-danger'>
                Your username cannot be longer than 50 characters.
              </small>
              <small v-if='!$v.registerAccount.login.pattern' class='form-text text-danger'>
                Your username can only contain letters and digits.
              </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='email'>Email</label>
            <input
              id='email'
              v-model='$v.registerAccount.email.$model'
              :class='{ valid: !$v.registerAccount.email.$invalid, invalid: $v.registerAccount.email.$invalid }'
              class='form-control'
              data-cy='email'
              email
              maxlength='254'
              minlength='5'
              name='email'
              required
              type='email'
            />
            <div v-if='$v.registerAccount.email.$anyDirty && $v.registerAccount.email.$invalid'>
              <small v-if='!$v.registerAccount.email.required' class='form-text text-danger'> Your email is
                required. </small>
              <small v-if='!$v.registerAccount.email.email' class='form-text text-danger'> Your email is
                invalid. </small>
              <small v-if='!$v.registerAccount.email.minLength' class='form-text text-danger'>
                Your email is required to be at least 5 characters.
              </small>
              <small v-if='!$v.registerAccount.email.maxLength' class='form-text text-danger'>
                Your email cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='firstPassword'>New password</label>
            <input
              id='firstPassword'
              v-model='$v.registerAccount.password.$model'
              :class='{ valid: !$v.registerAccount.password.$invalid, invalid: $v.registerAccount.password.$invalid }'
              class='form-control'
              data-cy='firstPassword'
              maxlength='50'
              minlength='4'
              name='password'
              required
              type='password'
            />
            <div v-if='$v.registerAccount.password.$anyDirty && $v.registerAccount.password.$invalid'>
              <small v-if='!$v.registerAccount.password.required' class='form-text text-danger'> Your password is
                required. </small>
              <small v-if='!$v.registerAccount.password.minLength' class='form-text text-danger'>
                Your password is required to be at least 4 characters.
              </small>
              <small v-if='!$v.registerAccount.password.maxLength' class='form-text text-danger'>
                Your password cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='secondPassword'>New password confirmation</label>
            <input
              id='secondPassword'
              v-model='$v.confirmPassword.$model'
              :class='{ valid: !$v.confirmPassword.$invalid, invalid: $v.confirmPassword.$invalid }'
              class='form-control'
              data-cy='secondPassword'
              maxlength='50'
              minlength='4'
              name='confirmPasswordInput'
              required
              type='password'
            />
            <div v-if='$v.confirmPassword.$dirty && $v.confirmPassword.$invalid'>
              <small v-if='!$v.confirmPassword.required' class='form-text text-danger'> Your confirmation password is
                required. </small>
              <small v-if='!$v.confirmPassword.minLength' class='form-text text-danger'>
                Your confirmation password is required to be at least 4 characters.
              </small>
              <small v-if='!$v.confirmPassword.maxLength' class='form-text text-danger'>
                Your confirmation password cannot be longer than 50 characters.
              </small>
              <small v-if='!$v.confirmPassword.sameAsPassword' class='form-text text-danger'>
                The password and its confirmation do not match!
              </small>
            </div>
          </div>

          <button :disabled='$v.$invalid' class='btn btn-primary' data-cy='submit' type='submit'>Register</button>
        </form>
        <p></p>
        <div class='alert alert-warning'>
          <span>If you want to </span>
          <a class='alert-link' v-on:click='openLogin()'>sign in</a
          ><span
        >, you can try the default accounts:<br />- Administrator (login="admin" and password="admin") <br />- User (login="user" and
            password="user").</span
        >
        </div>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./register.component.ts'></script>
