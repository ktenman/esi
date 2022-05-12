<template>
  <div class='row justify-content-center'>
    <div class='col-8'>
      <div v-if='bookRentingRequest'>
        <h2 class='jh-entity-heading' data-cy='bookRentingRequestDetailsHeading'>
          <span>BookRentingRequest</span> {{ bookRentingRequest.id }}
        </h2>
        <dl class='row jh-entity-details'>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ bookRentingRequest.status }}</span>
          </dd>
          <dt>
            <span>Rented At</span>
          </dt>
          <dd>
            <span>{{ bookRentingRequest.rentedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Rented Until</span>
          </dt>
          <dd>
            <span>{{ bookRentingRequest.rentedUntil | formatDate }}</span>
          </dd>
          <dt>
            <span>Book</span>
          </dt>
          <dd>
            <div v-if='bookRentingRequest.book'>
              <router-link :to="{ name: 'BookView', params: { bookId: bookRentingRequest.book.id } }">{{
                  bookRentingRequest.book.id
                }}
              </router-link>
            </div>
          </dd>
        </dl>
        <button class='btn btn-info' data-cy='entityDetailsBackButton' type='submit'
                v-on:click.prevent='previousState()'>
          <font-awesome-icon icon='arrow-left'></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if='bookRentingRequest.id'
          v-slot='{ navigate }'
          :to="{ name: 'BookRentingRequestEdit', params: { bookRentingRequestId: bookRentingRequest.id } }"
          custom
        >
          <button class='btn btn-primary' @click='navigate'>
            <font-awesome-icon icon='pencil-alt'></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./book-renting-request-details.component.ts'></script>
