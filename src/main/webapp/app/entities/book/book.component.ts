import { mixins } from 'vue-class-component'
import { Component, Inject } from 'vue-property-decorator'
import Vue2Filters from 'vue2-filters'
import { IBook } from '@/shared/model/book.model'

import JhiDataUtils from '@/shared/data/data-utils.service'

import BookService from './book.service'
import AlertService from '@/shared/alert/alert.service'

@Component({
  mixins: [Vue2Filters.mixin]
})
export default class Book extends mixins(JhiDataUtils) {
  public itemsPerPage = 20
  public queryCount: number = null
  public page = 1
  public previousPage = 1
  public propOrder = 'id'
  public reverse = false
  public totalItems = 0
  public infiniteId = +new Date()
  public links = {}
  public books: IBook[] = []
  public isFetching = false
  @Inject('bookService') private bookService: () => BookService
  @Inject('alertService') private alertService: () => AlertService
  private removeId: number = null

  public mounted(): void {
    this.retrieveAllBooks()
  }

  public clear(): void {
    this.page = 1
    this.links = {}
    this.infiniteId += 1
    this.books = []
    this.retrieveAllBooks()
  }

  public reset(): void {
    this.page = 1
    this.infiniteId += 1
    this.books = []
    this.retrieveAllBooks()
  }

  public retrieveAllBooks(): void {
    this.isFetching = true
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    }
    this.bookService()
      .retrieve(paginationQuery)
      .then(
        res => {
          if (res.data && res.data.length > 0) {
            for (let i = 0; i < res.data.length; i++) {
              this.books.push(res.data[i])
            }
            if (res.headers && res.headers['link']) {
              this.links = this.parseLinks(res.headers['link'])
            }
          }
          this.totalItems = Number(res.headers['x-total-count'])
          this.queryCount = this.totalItems
          this.isFetching = false
          if (<any>this.$refs.infiniteLoading) {
            (<any>this.$refs.infiniteLoading).stateChanger.loaded()
            if (this.links !== {} && this.page > this.links['last']) {
              (<any>this.$refs.infiniteLoading).stateChanger.complete()
            }
          }
        },
        err => {
          this.isFetching = false
          this.alertService().showHttpError(this, err.response)
        }
      )
  }

  public handleSyncList(): void {
    this.clear()
  }

  public prepareRemove(instance: IBook): void {
    this.removeId = instance.id
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show()
    }
  }

  public removeBook(): void {
    this.bookService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Book is deleted with identifier ' + this.removeId
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000
        })
        this.removeId = null
        this.reset()
        this.closeDialog()
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response)
      })
  }

  public loadMore($state): void {
    if (!this.isFetching) {
      this.page++
      this.transition()
    }
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'desc' : 'asc')]
    if (this.propOrder !== 'id') {
      result.push('id')
    }
    return result
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page
      this.transition()
    }
  }

  public transition(): void {
    this.retrieveAllBooks()
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder
    this.reverse = !this.reverse
    this.reset()
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide()
  }
}
