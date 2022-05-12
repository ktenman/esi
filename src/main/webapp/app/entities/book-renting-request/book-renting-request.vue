<template>
  <div>
    <h2 id='page-heading' data-cy='BookRentingRequestHeading'>
      <span id='book-renting-request-heading'>Book Renting Requests</span>
      <div class='d-flex justify-content-end'>
        <button :disabled='isFetching' class='btn btn-info mr-2' v-on:click='handleSyncList'>
          <font-awesome-icon :spin='isFetching' icon='sync'></font-awesome-icon>
          <span>Refresh List</span>
        </button>
        <router-link v-slot='{ navigate }' :to="{ name: 'BookRentingRequestCreate' }" custom>
          <button
            id='jh-create-entity'
            class='btn btn-primary jh-create-entity create-book-renting-request'
            data-cy='entityCreateButton'
            @click='navigate'
          >
            <font-awesome-icon icon='plus'></font-awesome-icon>
            <span> Create a new Book Renting Request </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div v-if='!isFetching && bookRentingRequests && bookRentingRequests.length === 0' class='alert alert-warning'>
      <span>No bookRentingRequests found</span>
    </div>
    <div v-if='bookRentingRequests && bookRentingRequests.length > 0' class='table-responsive'>
      <table aria-describedby='bookRentingRequests' class='table table-striped'>
        <thead>
        <tr>
          <th scope='row' v-on:click="changeOrder('id')">
            <span>ID</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'id'" :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('status')">
            <span>Status</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'status'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('rentedAt')">
            <span>Rented At</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'rentedAt'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('rentedUntil')">
            <span>Rented Until</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'rentedUntil'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('book.id')">
            <span>Book</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'book.id'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row'></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for='bookRentingRequest in bookRentingRequests' :key='bookRentingRequest.id' data-cy='entityTable'>
          <td>
            <router-link
              :to="{ name: 'BookRentingRequestView', params: { bookRentingRequestId: bookRentingRequest.id } }">{{
                bookRentingRequest.id
              }}
            </router-link>
          </td>
          <td>{{ bookRentingRequest.status }}</td>
          <td>{{ bookRentingRequest.rentedAt | formatDate }}</td>
          <td>{{ bookRentingRequest.rentedUntil | formatDate }}</td>
          <td>
            <div v-if='bookRentingRequest.book'>
              <router-link :to="{ name: 'BookView', params: { bookId: bookRentingRequest.book.id } }">{{
                  bookRentingRequest.book.id
                }}
              </router-link>
            </div>
          </td>
          <td class='text-right'>
            <div class='btn-group'>
              <router-link
                v-slot='{ navigate }'
                :to="{ name: 'BookRentingRequestView', params: { bookRentingRequestId: bookRentingRequest.id } }"
                custom
              >
                <button class='btn btn-info btn-sm details' data-cy='entityDetailsButton' @click='navigate'>
                  <font-awesome-icon icon='eye'></font-awesome-icon>
                  <span class='d-none d-md-inline'>View</span>
                </button>
              </router-link>
              <router-link
                v-slot='{ navigate }'
                :to="{ name: 'BookRentingRequestEdit', params: { bookRentingRequestId: bookRentingRequest.id } }"
                custom
              >
                <button class='btn btn-primary btn-sm edit' data-cy='entityEditButton' @click='navigate'>
                  <font-awesome-icon icon='pencil-alt'></font-awesome-icon>
                  <span class='d-none d-md-inline'>Edit</span>
                </button>
              </router-link>
              <b-button
                v-b-modal.removeEntity
                class='btn btn-sm'
                data-cy='entityDeleteButton'
                variant='danger'
                v-on:click='prepareRemove(bookRentingRequest)'
              >
                <font-awesome-icon icon='times'></font-awesome-icon>
                <span class='d-none d-md-inline'>Delete</span>
              </b-button>
            </div>
          </td>
        </tr>
        </tbody>
        <infinite-loading
          v-if='totalItems > itemsPerPage'
          ref='infiniteLoading'
          slot='append'
          :distance='20'
          :identifier='infiniteId'
          force-use-infinite-wrapper='.el-table__body-wrapper'
          @infinite='loadMore'
        >
        </infinite-loading>
      </table>
    </div>
    <b-modal id='removeEntity' ref='removeEntity'>
      <span slot='modal-title'
      ><span id='libraryApp.bookRentingRequest.delete.question' data-cy='bookRentingRequestDeleteDialogHeading'
      >Confirm delete operation</span
      ></span
      >
      <div class='modal-body'>
        <p id='esi-delete-bookRentingRequest-heading'>Are you sure you want to delete this Book Renting Request?</p>
      </div>
      <div slot='modal-footer'>
        <button class='btn btn-secondary' type='button' v-on:click='closeDialog()'>Cancel</button>
        <button
          id='esi-confirm-delete-bookRentingRequest'
          class='btn btn-primary'
          data-cy='entityConfirmDeleteButton'
          type='button'
          v-on:click='removeBookRentingRequest()'
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang='ts' src='./book-renting-request.component.ts'></script>
