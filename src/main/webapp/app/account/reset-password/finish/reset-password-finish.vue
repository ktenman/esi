<template>
  <div>
    <div class='row justify-content-center'>
      <div class='col-md-8'>
        <h1>Reset your password</h1>

        <div v-if='keyMissing' class='alert alert-danger'>
          <strong>The password reset key is missing.</strong>
        </div>

        <div v-if='error' class='alert alert-danger'>
          <p>Your password couldn't be reset. Remember a password request is only valid for 24 hours.</p>
        </div>

        <div v-if='success' class='alert alert-success'>
          <span><strong>Your password had been reset.</strong> Please </span>
          <a class='alert-link' v-on:click='openLogin'>sign in</a>
        </div>
        <div v-if='doNotMatch' class='alert alert-danger'>
          <p>The password and its confirmation do not match!</p>
        </div>

        <div v-if='!success && !keyMissing' class='alert alert-warning'>
          <p>Choose a new password.</p>
        </div>

        <div v-if='!keyMissing'>
          <form v-if='!success' name='form' role='form' v-on:submit.prevent='finishReset()'>
            <div class='form-group'>
              <label class='form-control-label' for='newPassword'>New password</label>
              <input
                id='newPassword'
                v-model='$v.resetAccount.newPassword.$model'
                :class='{ valid: !$v.resetAccount.newPassword.$invalid, invalid: $v.resetAccount.newPassword.$invalid }'
                class='form-control'
                data-cy='resetPassword'
                maxlength='50'
                minlength='4'
                name='newPassword'
                required
                type='password'
              />
              <div v-if='$v.resetAccount.newPassword.$anyDirty && $v.resetAccount.newPassword.$invalid'>
                <small v-if='!$v.resetAccount.newPassword.required' class='form-text text-danger'> Your password is
                  required. </small>
                <small v-if='!$v.resetAccount.newPassword.minLength' class='form-text text-danger'>
                  Your password is required to be at least 4 characters.
                </small>
                <small v-if='!$v.resetAccount.newPassword.maxLength' class='form-text text-danger'>
                  Your password cannot be longer than 50 characters.
                </small>
              </div>
            </div>
            <div class='form-group'>
              <label class='form-control-label' for='confirmPassword'>New password confirmation</label>
              <input
                id='confirmPassword'
                v-model='$v.resetAccount.confirmPassword.$model'
                :class='{ valid: !$v.resetAccount.confirmPassword.$invalid, invalid: $v.resetAccount.confirmPassword.$invalid }'
                class='form-control'
                data-cy='confirmResetPassword'
                maxlength='50'
                minlength='4'
                name='confirmPassword'
                required
                type='password'
              />
              <div v-if='$v.resetAccount.confirmPassword.$anyDirty && $v.resetAccount.confirmPassword.$invalid'>
                <small v-if='!$v.resetAccount.confirmPassword.sameAsPassword' class='form-text text-danger'>
                  The password and its confirmation do not match!
                </small>
              </div>
            </div>
            <button :disabled='$v.resetAccount.$invalid' class='btn btn-primary' data-cy='submit' type='submit'>Save
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./reset-password-finish.component.ts'></script>
