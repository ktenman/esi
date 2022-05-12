<template>
  <div class='row justify-content-center'>
    <div class='col-8'>
      <form name='editForm' novalidate role='form' v-on:submit.prevent='save()'>
        <h2 id='libraryApp.book.home.createOrEditLabel' data-cy='BookCreateUpdateHeading'>Create or edit a Book</h2>
        <div>
          <div v-if='book.id' class='form-group'>
            <label for='id'>ID</label>
            <input id='id' v-model='book.id' class='form-control' name='id' readonly type='text' />
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-author'>Author</label>
            <input
              id='book-author'
              v-model='$v.book.author.$model'
              :class='{ valid: !$v.book.author.$invalid, invalid: $v.book.author.$invalid }'
              class='form-control'
              data-cy='author'
              name='author'
              type='text'
            />
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-title'>Title</label>
            <input
              id='book-title'
              v-model='$v.book.title.$model'
              :class='{ valid: !$v.book.title.$invalid, invalid: $v.book.title.$invalid }'
              class='form-control'
              data-cy='title'
              name='title'
              type='text'
            />
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-releaseDate'>Release Date</label>
            <b-input-group class='mb-3'>
              <b-input-group-prepend>
                <b-form-datepicker
                  v-model='$v.book.releaseDate.$model'
                  :locale='currentLanguage'
                  aria-controls='book-releaseDate'
                  button-only
                  class='form-control'
                  close-button
                  name='releaseDate'
                  reset-button
                  today-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id='book-releaseDate'
                v-model='$v.book.releaseDate.$model'
                :class='{ valid: !$v.book.releaseDate.$invalid, invalid: $v.book.releaseDate.$invalid }'
                class='form-control'
                data-cy='releaseDate'
                name='releaseDate'
                type='text'
              />
            </b-input-group>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-status'>Status</label>
            <select
              id='book-status'
              v-model='$v.book.status.$model'
              :class='{ valid: !$v.book.status.$invalid, invalid: $v.book.status.$invalid }'
              class='form-control'
              data-cy='status'
              name='status'
            >
              <option v-for='bookStatus in bookStatusValues' :key='bookStatus' v-bind:value='bookStatus'>{{
                  bookStatus
                }}
              </option>
            </select>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-category'>Category</label>
            <select
              id='book-category'
              v-model='$v.book.category.$model'
              :class='{ valid: !$v.book.category.$invalid, invalid: $v.book.category.$invalid }'
              class='form-control'
              data-cy='category'
              name='category'
            >
              <option v-for='category in categoryValues' :key='category' v-bind:value='category'>{{ category }}</option>
            </select>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-language'>Language</label>
            <select
              id='book-language'
              v-model='$v.book.language.$model'
              :class='{ valid: !$v.book.language.$invalid, invalid: $v.book.language.$invalid }'
              class='form-control'
              data-cy='language'
              name='language'
            >
              <option v-for='language in languageValues' :key='language' v-bind:value='language'>{{ language }}</option>
            </select>
          </div>
        </div>
        <div>
          <button id='cancel-save' class='btn btn-secondary' data-cy='entityCreateCancelButton' type='button'
                  v-on:click='previousState()'>
            <font-awesome-icon icon='ban'></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            id='save-entity'
            :disabled='$v.book.$invalid || isSaving'
            class='btn btn-primary'
            data-cy='entityCreateSaveButton'
            type='submit'
          >
            <font-awesome-icon icon='save'></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang='ts' src='./book-update.component.ts'></script>
