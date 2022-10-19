// contracts/Artoken.sol
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@klaytn/contracts/KIP/token/KIP17/extensions/KIP17URIStorage.sol";
import "./utils/PermitControl.sol";

contract Artoken is KIP17URIStorage, PermitControl {
    struct WorkInfo {
        uint256 workId;
        uint256 workIndex;
    }

    string private _hostUrl;

    mapping(uint256 => uint256) private _workCounter;
    mapping(uint256 => WorkInfo) private _workOfNFT;

    constructor() KIP17("Artoken", "ART") {
        _hostUrl = "http://34.212.84.161/";
    }

    function mintArt(address to, uint256 newItemId, uint256 workId)
        public
        onlyMinter
        returns (uint256)
    {
        address buyer = to;
        _mint(buyer, newItemId);

        string memory url = string.concat(_hostUrl, "nftInfo/");
        url = string.concat(url, Strings.toHexString(uint160(address(this)), 20));
        url = string.concat(url, "/");
        url = string.concat(url, Strings.toString(workId));
        _setTokenURI(newItemId, url);

        _workCounter[workId] = _workCounter[workId] + 1;
        _workOfNFT[newItemId].workId = workId;
        _workOfNFT[newItemId].workIndex = _workCounter[workId];

        return newItemId;
    }

    function getHostURL() external view returns(string memory) {
        return _hostUrl;
    }

    function setHostURL(string memory hostUrl) public onlyOwner {
        _hostUrl = hostUrl;
    }

    function getWorkTotalCount(uint256 workId) external view returns(uint256) {
        return _workCounter[workId];
    }

    function getWorkInfo(uint256 nftId) external view returns(WorkInfo memory) {
        return _workOfNFT[nftId];
    }
}