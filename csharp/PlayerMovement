using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{

    [Header("Movement")]
    public CharacterController controller;
    public LayerMask groundMask;
    public float legLength;
    public float walkSpeed;
    public float sprintSpeed;
    public float maxSprintTime;
    public float jumpForce;
    public float gravity = -9.81f;

    private float movementSpeed;
    private float sprintTime = 0f;
    private float sprintCoolDownTime;
    private bool isCrouching;
    private bool isSprinting;
    private bool canSprint;
    private bool isGrounded;
    private float moveX;
    private float moveZ;
    private Vector3 velocity;
    

    // Start is called before the first frame update
    void Start()
    {
        movementSpeed = walkSpeed;
        sprintTime = maxSprintTime;
    }

    // Update is called once per frame
    void Update()
    {
        Move();
    }

    void Move()
    {
        // Movement
        // X and Z Axis
        moveX = Input.GetAxis("Horizontal");
        moveZ = Input.GetAxis("Vertical");
        Vector3 move = transform.right * moveX + transform.forward * moveZ;

        // Sprinting
        sprintTime = Mathf.Clamp(sprintTime, 0f, maxSprintTime);
        if (isSprinting && sprintTime > 0 && canSprint)
        {
            movementSpeed = sprintSpeed;
            sprintTime -= Time.deltaTime;
        }
        else
        {
            movementSpeed = walkSpeed;
            sprintTime += Time.deltaTime;
            canSprint = false;
            if (sprintTime >= sprintCoolDownTime) canSprint = true;
        }
        controller.Move(move * movementSpeed * Time.deltaTime);

        // Jumping
        isGrounded = Physics.Raycast(transform.position, Vector3.down, legLength, groundMask);

        if (Input.GetButtonDown("Jump") && isGrounded)
        {
            velocity.y = Mathf.Sqrt(jumpForce * -2f * gravity);
        }

        velocity.y += gravity * Time.deltaTime;
        controller.Move(velocity * Time.deltaTime);

        if (isGrounded && velocity.y < 0)
        {
            velocity.y = -2f;
        }

        if (isCrouching)
        {
            transform.localScale = new Vector3(1, 0.7f, 1);
        }
        else
        {
            transform.localScale = new Vector3(1, 1, 1);
        }
    }
}
