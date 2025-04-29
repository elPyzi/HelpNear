import { BaseError, Jsonable } from './BaseError';

export class ResponseError extends BaseError {
  public readonly statusCode: number;
  public readonly responseMessage: string;

  constructor(
    message: string,
    statusCode: number,
    responseMessage: string,
    options: { error?: Error; context?: Jsonable; cause?: Error } = {},
  ) {
    super(message, options);
    this.statusCode = statusCode;
    this.responseMessage = responseMessage;
  }
}
