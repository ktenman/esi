<template>
  <div>
    <div class='row justify-content-center'>
      <div class='col-md-8 toastify-container'>
        <h2 v-if='username' id='settings-title'>
          <span>
            User settings for [
            <strong>
              {{ username }}
            </strong>
            ]
          </span>
        </h2>

        <div v-if='success' class='alert alert-success' role='alert'>
          <strong>Settings saved!</strong>
        </div>

        <div v-if='errorEmailExists' class='alert alert-danger' role='alert'>
          <strong>Email is already in use!</strong> Please choose another one.
        </div>

        <form v-if='settingsAccount' id='settings-form' name='form' novalidate role='form' v-on:submit.prevent='save()'>
          <div class='form-group'>
            <label class='form-control-label' for='firstName'>First Name</label>
            <input
              id='firstName'
              v-model='$v.settingsAccount.firstName.$model'
              :class='{ valid: !$v.settingsAccount.firstName.$invalid, invalid: $v.settingsAccount.firstName.$invalid }'
              class='form-control'
              data-cy='firstname'
              maxlength='50'
              minlength='1'
              name='firstName'
              required
              type='text'
            />
            <div v-if='$v.settingsAccount.firstName.$anyDirty && $v.settingsAccount.firstName.$invalid'>
              <small v-if='!$v.settingsAccount.firstName.required' class='form-text text-danger'> Your first name is
                required. </small>
              <small v-if='!$v.settingsAccount.firstName.minLength' class='form-text text-danger'>
                Your first name is required to be at least 1 character.
              </small>
              <small v-if='!$v.settingsAccount.firstName.maxLength' class='form-text text-danger'>
                Your first name cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='lastName'>Last Name</label>
            <input
              id='lastName'
              v-model='$v.settingsAccount.lastName.$model'
              :class='{ valid: !$v.settingsAccount.lastName.$invalid, invalid: $v.settingsAccount.lastName.$invalid }'
              class='form-control'
              data-cy='lastname'
              maxlength='50'
              minlength='1'
              name='lastName'
              required
              type='text'
            />
            <div v-if='$v.settingsAccount.lastName.$anyDirty && $v.settingsAccount.lastName.$invalid'>
              <small v-if='!$v.settingsAccount.lastName.required' class='form-text text-danger'> Your last name is
                required. </small>
              <small v-if='!$v.settingsAccount.lastName.minLength' class='form-text text-danger'>
                Your last name is required to be at least 1 character.
              </small>
              <small v-if='!$v.settingsAccount.lastName.maxLength' class='form-text text-danger'>
                Your last name cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='email'>Email</label>
            <input
              id='email'
              v-model='$v.settingsAccount.email.$model'
              :class='{ valid: !$v.settingsAccount.email.$invalid, invalid: $v.settingsAccount.email.$invalid }'
              class='form-control'
              data-cy='email'
              email
              maxlength='254'
              minlength='5'
              name='email'
              required
              type='email'
            />
            <div v-if='$v.settingsAccount.email.$anyDirty && $v.settingsAccount.email.$invalid'>
              <small v-if='!$v.settingsAccount.email.required' class='form-text text-danger'> Your email is
                required. </small>
              <small v-if='!$v.settingsAccount.email.email' class='form-text text-danger'> Your email is
                invalid. </small>
              <small v-if='!$v.settingsAccount.email.minLength' class='form-text text-danger'>
                Your email is required to be at least 5 characters.
              </small>
              <small v-if='!$v.settingsAccount.email.maxLength' class='form-text text-danger'>
                Your email cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div v-if='languages && Object.keys(languages).length > 1' class='form-group'>
            <label for='langKey'>Language</label>
            <select id='langKey' v-model='settingsAccount.langKey' class='form-control' data-cy='langKey'
                    name='langKey'>
              <option v-for='(language, key) in languages' :key='`lang-${key}`' :value='key'>{{
                  language.name
                }}
              </option>
            </select>
          </div>
          <button :disabled='$v.settingsAccount.$invalid' class='btn btn-primary' data-cy='submit' type='submit'>Save
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./settings.component.ts'></script>
