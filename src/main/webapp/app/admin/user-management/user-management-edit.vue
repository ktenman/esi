<template>
  <div class='row justify-content-center'>
    <div class='col-8'>
      <form v-if='userAccount' name='editForm' novalidate role='form' v-on:submit.prevent='save()'>
        <h2 id='myUserLabel'>Create or edit a User</h2>
        <div>
          <div :hidden='!userAccount.id' class='form-group'>
            <label>ID</label>
            <input v-model='userAccount.id' class='form-control' name='id' readonly type='text' />
          </div>

          <div class='form-group'>
            <label class='form-control-label'>Login</label>
            <input
              v-model='$v.userAccount.login.$model'
              :class='{ valid: !$v.userAccount.login.$invalid, invalid: $v.userAccount.login.$invalid }'
              class='form-control'
              name='login'
              type='text'
            />

            <div v-if='$v.userAccount.login.$anyDirty && $v.userAccount.login.$invalid'>
              <small v-if='!$v.userAccount.login.required' class='form-text text-danger'> This field is
                required. </small>

              <small v-if='!$v.userAccount.login.maxLength' class='form-text text-danger'>
                This field cannot be longer than 50 characters.
              </small>

              <small v-if='!$v.userAccount.login.pattern' class='form-text text-danger'>
                This field can only contain letters, digits and e-mail addresses.
              </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='firstName'>First Name</label>
            <input
              id='firstName'
              v-model='$v.userAccount.firstName.$model'
              :class='{ valid: !$v.userAccount.firstName.$invalid, invalid: $v.userAccount.firstName.$invalid }'
              class='form-control'
              name='firstName'
              type='text'
            />
            <div v-if='$v.userAccount.firstName.$anyDirty && $v.userAccount.firstName.$invalid'>
              <small v-if='!$v.userAccount.firstName.maxLength' class='form-text text-danger'>
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='lastName'>Last Name</label>
            <input
              id='lastName'
              v-model='$v.userAccount.lastName.$model'
              :class='{ valid: !$v.userAccount.lastName.$invalid, invalid: $v.userAccount.lastName.$invalid }'
              class='form-control'
              name='lastName'
              type='text'
            />
            <div v-if='$v.userAccount.lastName.$anyDirty && $v.userAccount.lastName.$invalid'>
              <small v-if='!$v.userAccount.lastName.maxLength' class='form-text text-danger'>
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='email'>Email</label>
            <input
              id='email'
              v-model='$v.userAccount.email.$model'
              :class='{ valid: !$v.userAccount.email.$invalid, invalid: $v.userAccount.email.$invalid }'
              class='form-control'
              email
              name='email'
              required
              type='email'
            />
            <div v-if='$v.userAccount.email.$anyDirty && $v.userAccount.email.$invalid'>
              <small v-if='!$v.userAccount.email.required' class='form-text text-danger'> Your email is
                required. </small>
              <small v-if='!$v.userAccount.email.email' class='form-text text-danger'> Your email is invalid. </small>
              <small v-if='!$v.userAccount.email.minLength' class='form-text text-danger'>
                Your email is required to be at least 5 characters.
              </small>
              <small v-if='!$v.userAccount.email.maxLength' class='form-text text-danger'>
                Your email cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class='form-check'>
            <label class='form-check-label' for='activated'>
              <input
                id='activated'
                v-model='userAccount.activated'
                :disabled='userAccount.id === null'
                class='form-check-input'
                name='activated'
                type='checkbox'
              />
              <span>Activated</span>
            </label>
          </div>

          <div class='form-group'>
            <label>Profiles</label>
            <select v-model='userAccount.authorities' class='form-control' multiple name='authority'>
              <option v-for='authority of authorities' :key='authority' :value='authority'>{{ authority }}</option>
            </select>
          </div>
        </div>
        <div>
          <button class='btn btn-secondary' type='button' v-on:click='previousState()'>
            <font-awesome-icon icon='ban'></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button :disabled='$v.userAccount.$invalid || isSaving' class='btn btn-primary' type='submit'>
            <font-awesome-icon icon='save'></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang='ts' src='./user-management-edit.component.ts'></script>
