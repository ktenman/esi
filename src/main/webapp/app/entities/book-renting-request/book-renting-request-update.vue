<template>
  <div class='row justify-content-center'>
    <div class='col-8'>
      <form name='editForm' novalidate role='form' v-on:submit.prevent='save()'>
        <h2 id='libraryApp.bookRentingRequest.home.createOrEditLabel' data-cy='BookRentingRequestCreateUpdateHeading'>
          Create or edit a BookRentingRequest
        </h2>
        <div>
          <div v-if='bookRentingRequest.id' class='form-group'>
            <label for='id'>ID</label>
            <input id='id' v-model='bookRentingRequest.id' class='form-control' name='id' readonly type='text' />
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-renting-request-status'>Status</label>
            <select
              id='book-renting-request-status'
              v-model='$v.bookRentingRequest.status.$model'
              :class='{ valid: !$v.bookRentingRequest.status.$invalid, invalid: $v.bookRentingRequest.status.$invalid }'
              class='form-control'
              data-cy='status'
              name='status'
            >
              <option v-for='rentingStatus in rentingStatusValues' :key='rentingStatus' v-bind:value='rentingStatus'>
                {{ rentingStatus }}
              </option>
            </select>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-renting-request-rentedAt'>Rented At</label>
            <div class='d-flex'>
              <input
                id='book-renting-request-rentedAt'
                :class='{ valid: !$v.bookRentingRequest.rentedAt.$invalid, invalid: $v.bookRentingRequest.rentedAt.$invalid }'
                :value='convertDateTimeFromServer($v.bookRentingRequest.rentedAt.$model)'
                class='form-control'
                data-cy='rentedAt'
                name='rentedAt'
                type='datetime-local'
                @change="updateInstantField('rentedAt', $event)"
              />
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-renting-request-rentedUntil'>Rented Until</label>
            <div class='d-flex'>
              <input
                id='book-renting-request-rentedUntil'
                :class='{ valid: !$v.bookRentingRequest.rentedUntil.$invalid, invalid: $v.bookRentingRequest.rentedUntil.$invalid }'
                :value='convertDateTimeFromServer($v.bookRentingRequest.rentedUntil.$model)'
                class='form-control'
                data-cy='rentedUntil'
                name='rentedUntil'
                type='datetime-local'
                @change="updateInstantField('rentedUntil', $event)"
              />
            </div>
          </div>
          <div class='form-group'>
            <label class='form-control-label' for='book-renting-request-book'>Book</label>
            <select id='book-renting-request-book' v-model='bookRentingRequest.book' class='form-control' data-cy='book'
                    name='book'>
              <option v-bind:value='null'></option>
              <option
                v-for='bookOption in books'
                :key='bookOption.id'
                v-bind:value='
                  bookRentingRequest.book && bookOption.id === bookRentingRequest.book.id ? bookRentingRequest.book : bookOption
                '
              >
                {{ bookOption.id }}
              </option>
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
            :disabled='$v.bookRentingRequest.$invalid || isSaving'
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
<script lang='ts' src='./book-renting-request-update.component.ts'></script>
