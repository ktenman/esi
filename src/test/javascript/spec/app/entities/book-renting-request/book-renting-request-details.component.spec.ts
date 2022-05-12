/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BookRentingRequestDetailComponent from '@/entities/book-renting-request/book-renting-request-details.vue';
import BookRentingRequestClass from '@/entities/book-renting-request/book-renting-request-details.component';
import BookRentingRequestService from '@/entities/book-renting-request/book-renting-request.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('BookRentingRequest Management Detail Component', () => {
    let wrapper: Wrapper<BookRentingRequestClass>;
    let comp: BookRentingRequestClass;
    let bookRentingRequestServiceStub: SinonStubbedInstance<BookRentingRequestService>;

    beforeEach(() => {
      bookRentingRequestServiceStub = sinon.createStubInstance<BookRentingRequestService>(BookRentingRequestService);

      wrapper = shallowMount<BookRentingRequestClass>(BookRentingRequestDetailComponent, {
        store,
        localVue,
        router,
        provide: { bookRentingRequestService: () => bookRentingRequestServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBookRentingRequest = { id: 123 };
        bookRentingRequestServiceStub.find.resolves(foundBookRentingRequest);

        // WHEN
        comp.retrieveBookRentingRequest(123);
        await comp.$nextTick();

        // THEN
        expect(comp.bookRentingRequest).toBe(foundBookRentingRequest);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBookRentingRequest = { id: 123 };
        bookRentingRequestServiceStub.find.resolves(foundBookRentingRequest);

        // WHEN
        comp.beforeRouteEnter({ params: { bookRentingRequestId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.bookRentingRequest).toBe(foundBookRentingRequest);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
