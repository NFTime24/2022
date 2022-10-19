const AbiHelper = artifacts.require("AbiHelper");

/*
 * uncomment accounts to access the test accounts made available by the
 * Ethereum client
 * See docs: https://www.trufflesuite.com/docs/truffle/testing/writing-tests-in-javascript
 */
contract("AbiHelper", function (/* accounts */) {
  before(async function(){
    this.abiHelper = await AbiHelper.new();
  });

  it("should assert true", async function () {
    return assert.isTrue(true);
  });

  it("get ABI Bytes", async function(){
    console.log(await this.abiHelper.getEncodedBytes());
  })
});
