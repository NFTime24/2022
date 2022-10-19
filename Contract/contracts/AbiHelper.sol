// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract AbiHelper {
    function getEncodedBytes() external pure returns (bytes memory){
        address _to = 0x7c07C1579aD1980863c83876EC4bec43BC8d6dFa;
        return abi.encodeWithSignature("mintArt(address, uint256, uint256)", _to, 3000, 3000);
    }
}