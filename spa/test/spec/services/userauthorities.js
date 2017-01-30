'use strict';

describe('Service: UserAuthorities', function () {

  // load the service's module
  beforeEach(module('roomChatApp'));

  // instantiate service
  var UserAuthorities;
  beforeEach(inject(function (_UserAuthorities_) {
    UserAuthorities = _UserAuthorities_;
  }));

  it('should do something', function () {
    expect(!!UserAuthorities).toBe(true);
  });

});
