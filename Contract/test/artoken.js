const { expect } = require('chai');
const Artoken = artifacts.require("artoken");

contract("Artoken", function (accounts) {
  const owner = accounts[0];

  before(async function(){
    this.artoken = await Artoken.new();
  });

  it('mint', async function () {
    // Store a value
    await this.artoken.mintArt(owner, 2, 3);

    // Test if the returned value is the same one
    // Note that we need to use strings to compare the 256 bit integers
    expect((await this.artoken.ownerOf(2)).toString()).to.equal(owner);
  });

  it('mint from other', async function () {
    // Store a value
    await this.artoken.mintArt(accounts[1], 3, 3, {from: accounts[1]});

    // Test if the returned value is the same one
    // Note that we need to use strings to compare the 256 bit integers
    expect((await this.artoken.ownerOf(3)).toString()).to.equal(accounts[1]);
  });

  it('Permit and mint', async function () {
    await this.artoken.permitMinter(accounts[1]);

    // Store a value
    await this.artoken.mintArt(accounts[1], 3, 3, {from: accounts[1]});

    // Test if the returned value is the same one
    // Note that we need to use strings to compare the 256 bit integers
    expect((await this.artoken.ownerOf(3)).toString()).to.equal(accounts[1]);
  });

  it('Get some data', async function () {
    console.log(await this.artoken.getHostURL());
    console.log(await this.artoken.tokenURI(3));
    console.log(await this.artoken.getWorkTotalCount(3));
    console.log(await this.artoken.getWorkInfo(2));
    console.log(await this.artoken.getWorkInfo(3));
  });
});
