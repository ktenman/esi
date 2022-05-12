import { IBook } from '@/shared/model/book.model'

import { RentingStatus } from '@/shared/model/enumerations/renting-status.model'

export interface IBookRentingRequest {
  id?: number;
  status?: RentingStatus | null;
  rentedAt?: Date | null;
  rentedUntil?: Date | null;
  book?: IBook | null;
}

export class BookRentingRequest implements IBookRentingRequest {
  constructor(
    public id?: number,
    public status?: RentingStatus | null,
    public rentedAt?: Date | null,
    public rentedUntil?: Date | null,
    public book?: IBook | null
  ) {
  }
}
