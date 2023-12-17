import React from 'react';

const MovesComponent = ({ moves }) => {
    return (
        <ul>
            {
                moves.map((move, index) => (
                <li key= {index}>Player : {move.player} => move {move.pitIndex + 1}</li>
            ))}
        </ul>
    );
};

export default MovesComponent;
