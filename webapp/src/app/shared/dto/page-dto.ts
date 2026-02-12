export interface IPage<T> {
  content: T;
  page: TPage;
}

export type TPage = {
  size: number;
  number: number;
  totalPages: number;
  totalElements: number;
}
