import { IBookRentingRequest } from '@/shared/model/book-renting-request.model'

import { BookStatus } from '@/shared/model/enumerations/book-status.model'
import { Category } from '@/shared/model/enumerations/category.model'
import { Language } from '@/shared/model/enumerations/language.model'

export interface IBook {
  id?: number;
  author?: string | null;
  title?: string | null;
  releaseDate?: Date | null;
  status?: BookStatus | null;
  category?: Category | null;
  language?: Language | null;
  bookRentingRequests?: IBookRentingRequest[] | null;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public author?: string | null,
    public title?: string | null,
    public releaseDate?: Date | null,
    public status?: BookStatus | null,
    public category?: Category | null,
    public language?: Language | null,
    public bookRentingRequests?: IBookRentingRequest[] | null
  ) {
  }
}
