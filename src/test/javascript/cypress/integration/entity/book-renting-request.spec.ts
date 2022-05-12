import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('BookRentingRequest e2e test', () => {
  const bookRentingRequestPageUrl = '/book-renting-request';
  const bookRentingRequestPageUrlPattern = new RegExp('/book-renting-request(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bookRentingRequestSample = {};

  let bookRentingRequest: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/book-renting-requests+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/book-renting-requests').as('postEntityRequest');
    cy.intercept('DELETE', '/api/book-renting-requests/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bookRentingRequest) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/book-renting-requests/${bookRentingRequest.id}`,
      }).then(() => {
        bookRentingRequest = undefined;
      });
    }
  });

  it('BookRentingRequests menu should load BookRentingRequests page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('book-renting-request');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BookRentingRequest').should('exist');
    cy.url().should('match', bookRentingRequestPageUrlPattern);
  });

  describe('BookRentingRequest page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bookRentingRequestPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BookRentingRequest page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/book-renting-request/new$'));
        cy.getEntityCreateUpdateHeading('BookRentingRequest');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bookRentingRequestPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/book-renting-requests',
          body: bookRentingRequestSample,
        }).then(({ body }) => {
          bookRentingRequest = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/book-renting-requests+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/book-renting-requests?page=0&size=20>; rel="last",<http://localhost/api/book-renting-requests?page=0&size=20>; rel="first"',
              },
              body: [bookRentingRequest],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bookRentingRequestPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BookRentingRequest page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bookRentingRequest');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bookRentingRequestPageUrlPattern);
      });

      it('edit button click should load edit BookRentingRequest page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BookRentingRequest');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bookRentingRequestPageUrlPattern);
      });

      it('last delete button click should delete instance of BookRentingRequest', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('bookRentingRequest').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bookRentingRequestPageUrlPattern);

        bookRentingRequest = undefined;
      });
    });
  });

  describe('new BookRentingRequest page', () => {
    beforeEach(() => {
      cy.visit(`${bookRentingRequestPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BookRentingRequest');
    });

    it('should create an instance of BookRentingRequest', () => {
      cy.get(`[data-cy="status"]`).select('OPEN');

      cy.get(`[data-cy="rentedAt"]`).type('2022-05-09T18:45').should('have.value', '2022-05-09T18:45');

      cy.get(`[data-cy="rentedUntil"]`).type('2022-05-10T01:54').should('have.value', '2022-05-10T01:54');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        bookRentingRequest = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', bookRentingRequestPageUrlPattern);
    });
  });
});
