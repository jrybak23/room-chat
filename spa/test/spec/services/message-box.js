'use strict';

describe('Service: messageBox', function () {

  // load the service's module
  beforeEach(module('roomChatApp'));

  // instantiate service
  var messageBox;
  beforeEach(inject(function (_messageBox_) {
    messageBox = _messageBox_;
  }));

  it('should do something', function () {
    expect(!!messageBox).toBe(true);
  });

});
