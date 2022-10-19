const Artoken = artifacts.require('Artoken');
const AbiHelper = artifacts.require('AbiHelper');

module.exports = async function (deployer) {
  await deployer.deploy(Artoken);
  // await deployer.deploy(AbiHelper);
};