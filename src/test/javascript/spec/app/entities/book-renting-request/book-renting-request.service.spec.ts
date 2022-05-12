/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import BookRentingRequestService from '@/entities/book-renting-request/book-renting-request.service';
import { BookRentingRequest } from '@/shared/model/book-renting-request.model';
import { RentingStatus } from '@/shared/model/enumerations/renting-status.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('BookRentingRequest Service', () => {
    let service: BookRentingRequestService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new BookRentingRequestService();
      currentDate = new Date();
      elemDefault = new BookRentingRequest(123, RentingStatus.OPEN, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            rentedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            rentedUntil: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a BookRentingRequest', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            rentedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            rentedUntil: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            rentedAt: currentDate,
            rentedUntil: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a BookRentingRequest', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a BookRentingRequest', async () => {
        const returnedFromService = Object.assign(
          {
            status: 'BBBBBB',
            rentedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            rentedUntil: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rentedAt: currentDate,
            rentedUntil: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a BookRentingRequest', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a BookRentingRequest', async () => {
        const patchObject = Object.assign({}, new BookRentingRequest());
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            rentedAt: currentDate,
            rentedUntil: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a BookRentingRequest', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of BookRentingRequest', async () => {
        const returnedFromService = Object.assign(
          {
            status: 'BBBBBB',
            rentedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            rentedUntil: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            rentedAt: currentDate,
            rentedUntil: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of BookRentingRequest', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a BookRentingRequest', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a BookRentingRequest', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
