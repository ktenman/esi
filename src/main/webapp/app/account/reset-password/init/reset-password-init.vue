<template>
  <div>
    <div class='row justify-content-center'>
      <div class='col-md-8'>
        <h1>Reset your password</h1>

        <div v-if='!success' class='alert alert-warning'>
          <p>Enter the email address you used to register.</p>
        </div>

        <div v-if='success' class='alert alert-success'>
          <p>Check your emails for details on how to reset your password.</p>
        </div>

        <form v-if='!success' name='form' role='form' v-on:submit.prevent='requestReset()'>
          <div class='form-group'>
            <label class='form-control-label' for='email'>Email</label>
            <input
              id='email'
              v-model='$v.resetAccount.email.$model'
              :class='{ valid: !$v.resetAccount.email.$invalid, invalid: $v.resetAccount.email.$invalid }'
              class='form-control'
              data-cy='emailResetPassword'
              email
              maxlength='254'
              minlength='5'
              name='email'
              required
              type='email'
            />
            <div v-if='$v.resetAccount.email.$anyDirty && $v.resetAccount.email.$invalid'>
              <small v-if='!$v.resetAccount.email.required' class='form-text text-danger'> Your email is
                required. </small>
              <small v-if='!$v.resetAccount.email.email' class='form-text text-danger'> Your email is invalid. </small>
              <small v-if='!$v.resetAccount.email.minLength' class='form-text text-danger'>
                Your email is required to be at least 5 characters.
              </small>
              <small v-if='!$v.resetAccount.email.maxLength' class='form-text text-danger'>
                Your email cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <button :disabled='$v.resetAccount.$invalid' class='btn btn-primary' data-cy='submit' type='submit'>Reset
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./reset-password-init.component.ts'></script>
