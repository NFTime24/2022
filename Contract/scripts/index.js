module.exports = async function main (callback) {
    try {
        const accounts = await web3.eth.getAccounts();
        const owner = accounts[0];
        
        const Artoken = artifacts.require('Artoken');
        const artoken = await Artoken.deployed();

        // await artoken.permitMinter("0x7c07C1579aD1980863c83876EC4bec43BC8d6dFa");
        // console.log(await artoken.isMinter("0x7c07C1579aD1980863c83876EC4bec43BC8d6dFa"));

        await artoken.mintArt(owner, 0, 0);
  
        callback(0);
    } catch (error) {
        console.error(error);
        callback(1);
    }
};