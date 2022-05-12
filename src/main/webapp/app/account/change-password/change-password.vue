<template>
  <div>
    <div class='row justify-content-center'>
      <div class='col-md-8 toastify-container'>
        <h2 v-if='account' id='password-title'>
          <span>
            Password for [<strong>{{ username }}</strong
          >]</span
          >
        </h2>

        <div v-if='success' class='alert alert-success' role='alert'>
          <strong>Password changed!</strong>
        </div>
        <div v-if='error' class='alert alert-danger' role='alert'>
          <strong>An error has occurred!</strong> The password could not be changed.
        </div>

        <div v-if='doNotMatch' class='alert alert-danger' role='alert'>The password and its confirmation do not match!
        </div>

        <form id='password-form' name='form' role='form' v-on:submit.prevent='changePassword()'>
          <div class='form-group'>
            <label class='form-control-label' for='currentPassword'>Current password</label>
            <input
              id='currentPassword'
              v-model='$v.resetPassword.currentPassword.$model'
              :class='{ valid: !$v.resetPassword.currentPassword.$invalid, invalid: $v.resetPassword.currentPassword.$invalid }'
              class='form-control'
              data-cy='currentPassword'
              name='currentPassword'
              required
              type='password'
            />
            <div v-if='$v.resetPassword.currentPassword.$anyDirty && $v.resetPassword.currentPassword.$invalid'>
              <small v-if='!$v.resetPassword.currentPassword.required' class='form-text text-danger'> Your password is
                required. </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='newPassword'>New password</label>
            <input
              id='newPassword'
              v-model='$v.resetPassword.newPassword.$model'
              :class='{ valid: !$v.resetPassword.newPassword.$invalid, invalid: $v.resetPassword.newPassword.$invalid }'
              class='form-control'
              data-cy='newPassword'
              maxlength='50'
              minlength='4'
              name='newPassword'
              required
              type='password'
            />
            <div v-if='$v.resetPassword.newPassword.$anyDirty && $v.resetPassword.newPassword.$invalid'>
              <small v-if='!$v.resetPassword.newPassword.required' class='form-text text-danger'> Your password is
                required. </small>
              <small v-if='!$v.resetPassword.newPassword.minLength' class='form-text text-danger'>
                Your password is required to be at least 4 characters.
              </small>
              <small v-if='!$v.resetPassword.newPassword.maxLength' class='form-text text-danger'>
                Your password cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='confirmPassword'>New password confirmation</label>
            <input
              id='confirmPassword'
              v-model='$v.resetPassword.confirmPassword.$model'
              :class='{ valid: !$v.resetPassword.confirmPassword.$invalid, invalid: $v.resetPassword.confirmPassword.$invalid }'
              class='form-control'
              data-cy='confirmPassword'
              maxlength='50'
              minlength='4'
              name='confirmPassword'
              required
              type='password'
            />
            <div v-if='$v.resetPassword.confirmPassword.$anyDirty && $v.resetPassword.confirmPassword.$invalid'>
              <small v-if='!$v.resetAccount.confirmPassword.sameAsPassword' class='form-text text-danger'>
                The password and its confirmation do not match!
              </small>
            </div>
          </div>

          <button :disabled='$v.resetPassword.$invalid' class='btn btn-primary' data-cy='submit' type='submit'>Save
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./change-password.component.ts'></script>
